
public class DartMonkey extends Monkey{
	private int upgrade1Cost;
	private int upgrade2Cost;
	public DartMonkey() {
		super(200, 50, 1, 2);
		// TODO Auto-generated constructor stub
	}
	
	public void upgrade1() {
		if(Game.money >= upgrade1Cost) {
			super.setDamage(super.getDamage() + 1);
		}
	}
	
	public void upgrade2() {
		
	}

}
