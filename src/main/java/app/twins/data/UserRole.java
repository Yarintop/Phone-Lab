package app.twins.data;

public enum UserRole {
    PLAYER,MANAGER,ADMIN;

    public String getRole(){
        switch(this){
            case ADMIN:
                return "admin";
            case PLAYER:
                return "player";
            case MANAGER:
                return "manager";
            default:
                return null;
        }
    }
}
