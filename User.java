class User
{
	String name;
	String email;
	long mob;

	public User()
	{

	}
	public void showUser()
	{
		System.out.println("name :"+name);
		System.out.println("email :"+email);
		System.out.println("mob :"+mob);
	}
}
class Mainclass
{
	public static void main(String[] args) 
	{
	  	User u=new User();
	}
}