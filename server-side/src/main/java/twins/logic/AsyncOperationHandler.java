package twins.logic;

        import java.util.Date;
        import java.util.HashMap;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jms.annotation.JmsListener;
        import org.springframework.stereotype.Component;
        import org.springframework.transaction.annotation.Transactional;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import twins.boundaries.OperationBoundary;
        import twins.converters.OperationConverter;
        import twins.dao.OperationDao;
        import twins.dao.UserDao;
        import twins.dao.ItemDao;
        import twins.data.OperationEntity;

@Component
public class AsyncOperationHandler {
    private ObjectMapper jackson;
    private OperationConverter operationConverter;
    // Services
    private UserDao userDao;
    private ItemDao itemDao;
    private OperationDao operationDao;


    // Maybe injecting all the dao is overkill but we'll se later what is needed when
    // We'll implement real project specific functionality
    @Autowired
    public AsyncOperationHandler(UserDao userDao, ItemDao itemDao, OperationDao operationDao) {
        this.jackson = new ObjectMapper();
        this.userDao = userDao;
        this.itemDao = itemDao;
        this.operationDao = operationDao;
    }

    @Autowired
    public void setOperationConverter(OperationConverter operationConverter) {
        this.operationConverter = operationConverter;
    }


    // make sure this method never throw any exception - because there is no one to handle this exception
    @Transactional
    @JmsListener(destination = "opInbox") // receive messages from MOM
    public void handleJson (String json) {
        try {
            System.err.println("waiting 3s to handle: " + json);
//            Thread.sleep(3L * 1000);
            // NOTE: this stalls handling the rest of processing for 3 whole seconds - just for demo sake
            // As Eyal said we might not need async operations in our project
            // So first we
            OperationBoundary boundary = this.jackson
                    .readValue(json, OperationBoundary.class);


            boundary.getOperationAttributes().put("finished", true);
            OperationEntity entity = this.operationConverter.toEntity(boundary);
            entity.setCreatedTimestamp(new Date());

            this.operationDao.save(entity);

            System.err.println("done handling message: " + json);

        } catch (Exception e) {
            // exception must be handled here
            e.printStackTrace();
        }
    }
}
