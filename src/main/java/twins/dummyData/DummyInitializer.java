package twins.dummyData;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.UserBoundary;
import twins.logic.OperationsService;
import twins.logic.UpdatedItemsService;
import twins.logic.UsersService;

// @Component
public class DummyInitializer implements CommandLineRunner{
	private UpdatedItemsService items;
    private UsersService users;
    private OperationsService operations;
    private DummyData dataGenerator;

	@Autowired
	public DummyInitializer(UpdatedItemsService items, UsersService users, OperationsService operations) {
		super();
		this.items = items;
		this.users = users;
		this.operations = operations;
	}

    @Autowired
    public void setDummyDataGenerator(DummyData dataGenerator) {
        this.dataGenerator = dataGenerator;
    }


	@Override
	public void run(String... args) throws Exception {
	    Random rand = new Random();
        // Create 3 Users - Player | Manager | Admin
        UserBoundary user = dataGenerator.getRandomUser();
        user.setRole("Player");
        user.setEmail("player@gmail.com");
        users.createUser(user);

        user.setRole("Manager");
        user.setEmail("manager@gmail.com");
        users.createUser(user);

        user.setRole("Admin");
        user.setEmail("admin@gmail.com");
        users.createUser(user);

        // Create 2 Items -- Parent | Child

        String space = "2021b.noam.levi1"; 
        String email = "manager@gmail.com";

        DigitalItemBoundary specialItem = this.items.createItem(space, email, dataGenerator.getRandomDigitalItem(space, email));

        // Create 10 items 
        for(int i = 0; i < 10; i++){
            DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
            randomItem.setActive(i % 2 == 0);
            randomItem.getItemAttributes().put("price", rand.nextDouble()*100);
            randomItem = this.items.createItem(space, email, randomItem);
            randomItem.getItemId().getSpace();
            this.items.bindChild(space, email, randomItem.getItemId().getSpace(), randomItem.getItemId().getId(), specialItem.getItemId());
            this.items.bindChild(space, email, specialItem.getItemId().getSpace(), specialItem.getItemId().getId(), randomItem.getItemId());
        }

        // Create 10 operations 
//        for(int i = 0; i < 10; i++){
//            DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
//            randomItem.setActive(i % 2 == 0);
//            randomItem = this.items.createItem(space, email, randomItem);
//            randomItem.getItemId().getSpace();
//            this.items.bindChild(space, email, randomItem.getItemId().getSpace(), randomItem.getItemId().getId(), specialItem.getItemId());
//            this.items.bindChild(space, email, specialItem.getItemId().getSpace(), specialItem.getItemId().getId(), randomItem.getItemId());
//        }
	}
}
