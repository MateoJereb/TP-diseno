package excepciones;

public class HabitacionInexistenteException extends Exception{
	public HabitacionInexistenteException() {
		super();
		
	}
	public HabitacionInexistenteException (String message) {
		super (message);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}

}
