package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Hashtable;

import database.DebugDB.Status;

public class DebugStatement implements Statement, Debuggable {

	Hashtable<String, DebugResultSet> resultsets = new Hashtable<String, DebugResultSet>();
	String id;
	Status statusinfo;
	String debuginfo;

	private Statement st;

	private DebugConnection father;

	public DebugStatement(String id, DebugConnection father, Statement st) {
		this.st = st;
		this.father = father;
		this.id = id;

		StringBuilder info = DebugConnection.createInfo("OPENED:",
				new Exception().getStackTrace());

		debuginfo = info.toString();
		statusinfo = Status.OPENED;

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return st.isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return st.unwrap(iface);
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		addBatch(sql);

	}

	@Override
	public void cancel() throws SQLException {
		cancel();

	}

	@Override
	public void clearBatch() throws SQLException {
		clearBatch();

	}

	@Override
	public void clearWarnings() throws SQLException {
		clearWarnings();

	}

	@Override
	public void close() throws SQLException {

		st.close();

		father.removeStatement(this);
		statusinfo = Status.CLOSED;
		StringBuilder info = DebugConnection.createInfo("CLOSED:",
				new Exception().getStackTrace());
		debuginfo += "|" + info;
	}

	@Override
	public boolean execute(String sql) throws SQLException {

		return st.execute(sql);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {

		return st.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {

		return st.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {

		return st.execute(sql, columnNames);
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return st.executeBatch();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		return addResultset(new DebugResultSet(nextId(), this,
				st.executeQuery(sql)));
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		return st.executeUpdate(sql);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return st.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return st.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return st.executeUpdate(sql, columnNames);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return st.getConnection();
	}

	@Override
	public int getFetchDirection() throws SQLException {

		return st.getFetchDirection();
	}

	@Override
	public int getFetchSize() throws SQLException {
		return st.getFetchSize();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {

		return addResultset(new DebugResultSet(nextId(), this,
				st.getGeneratedKeys()));
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return st.getMaxFieldSize();
	}

	@Override
	public int getMaxRows() throws SQLException {
		return st.getMaxRows();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return st.getMoreResults();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return st.getMoreResults(current);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return st.getQueryTimeout();
	}

	@Override
	public ResultSet getResultSet() throws SQLException {

		return st.getResultSet();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {

		return st.getResultSetConcurrency();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return st.getResultSetHoldability();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return st.getResultSetType();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return st.getUpdateCount();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return st.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return st.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return st.isPoolable();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		setCursorName(name);

	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		setEscapeProcessing(enable);

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		setFetchDirection(direction);

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		setFetchSize(rows);

	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		setMaxFieldSize(max);

	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		setMaxRows(max);

	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		setPoolable(poolable);

	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		setQueryTimeout(seconds);

	}

	public String getId() {

		return id;
	}

	public String getInfo() {
		return debuginfo;
	}

	protected ResultSet addResultset(DebugResultSet rs) {
		resultsets.put(rs.getId(), rs);
		return rs;
	}

	public void removeStatement(DebugResultSet debugResultSet) {
		resultsets.remove(debugResultSet.getId());

	}

	public String nextId() {
		return getId() + "_" + resultsets.size();
	}

	public  Hashtable<String, DebugResultSet> getResultSets() {
		
		return resultsets;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
