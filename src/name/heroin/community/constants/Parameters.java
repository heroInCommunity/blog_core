package name.heroin.community.constants;

public enum Parameters {
	I_DISPLAY_START(Constants.I_DISPLAY_START),
	I_DISPLAY_LENGTH(Constants.I_DISPLAY_LENGTH);
	
	private final String value;
	Parameters(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static class Constants {
		public static final String I_DISPLAY_START = "0";
		public static final String I_DISPLAY_LENGTH = "10";
	}
	
	public static boolean contains(String search) {
		for (Parameters parameter : Parameters.values()) {
			if (parameter.name().equals(search)) {
				return true;
			}
		}
		
		return false;
	}
}
