package enums;

public enum TipoFactura {

	A("A"),B("B");

    private final String name;

    TipoFactura(String name) {
        // TODO Auto-generated constructor stub
         this.name = name;
    }

    /**
     * @param name
     */

    public String getName() {
        return name;
    }
	
}
