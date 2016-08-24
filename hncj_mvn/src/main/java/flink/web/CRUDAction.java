package flink.web;

public abstract class CRUDAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3797956804611323349L;

	protected final String LIST = "list";
	protected final String ADD = "add";
	protected final String EDIT = "edit";
	protected final String DETAIL = "detail";

	public abstract String list() throws Exception;

	public abstract String toAdd() throws Exception;

	public abstract String doAdd() throws Exception;

	public abstract String toEdit() throws Exception;

	public abstract String doEdit() throws Exception;

	public abstract String delete() throws Exception;

	public abstract String detail() throws Exception;
}
