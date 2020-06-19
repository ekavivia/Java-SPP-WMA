package tool;

public class manage {
    private static String menus = "Data";
    private static String user = "Administrator";
    
    public static void setMenu(String menus){
        manage.menus = menus;
    }
    public static String getMenu(){
        return menus;
    }
    
    public static void setUser(String user){
        manage.user = user;
    }
    public static String getUser(){
        return user;
    }
}
