package keywordfw;

public class dwCommon {
	/*
	 * Description: Get url common of project .../src/ Name: URL_CM
	 */
	public static String URL_CM = System.getProperty("user.dir");

	/*
	 * Description: URL WebDriver use to test Name: URL_WD
	 */
	public static String URL_WD = URL_CM + "\\src\\com\\dw\\driver\\";

	/*
	 * Description: URL Website to test Name: URL_WS_*
	 */
	public static String URL_WS_SUMMARY = "Summary";
	public static String URL_WS_SCRIPT = "Script";

	/*
	 * Description: URL of INPUT, OUTPUT Folder Name: URL_IO
	 */
	public static String URL_IO = URL_CM + "\\INOUT\\";

	/*
	 * Description: Element of Excel File (File Name, Sheet Name, Column Name,
	 * Row Name) Name: EX_NAME
	 */
	public static String EX_NAME = URL_IO + "Auto-Script.xlsx";

	/*
	 * 
	 * 
	 */
	public static String FIELD_BROWSER = "Browser";
	public static String FIELD_TESTCASE = "Testcase";
	public static String FIELD_URL = "URL";
	public static String FIELD_DESCRIPTION = "Description";

	public static String FIELD_ID = "ID";
	public static String FIELD_ACTION = "Action";
	public static String FIELD_TYPE = "Type";
	public static String FIELD_ELEMENT = "Element";
	public static String FIELD_VALUE = "Values";
	public static String FIELD_USE_AUTO = "Automated";

	public static String SHEET_NAME = "TestReport";
	public static String FIELD_STATUS = "Status";

	/*
	 * Description: Handle read file excel
	 */
	public static readXLS HANDLE_READ_XLS = null;
	public static readXLS HANDLE_WRITE_XLS = null;

	public static void init() throws Exception {
		HANDLE_READ_XLS = new readXLS(EX_NAME);
	}

	public static void initResult(String fileName) throws Exception {
		HANDLE_WRITE_XLS = new readXLS(URL_CM + fileName);
	}

}
