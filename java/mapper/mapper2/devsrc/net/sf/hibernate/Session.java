package net.sf.hibernate;
import net.sf.hibernate.type.Type;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
public interface Session extends Serializable {
	public void flush() throws HibernateException;
	public void setFlushMode(FlushMode flushMode);
	public FlushMode getFlushMode();
	public SessionFactory getSessionFactory();
	public Connection connection() throws HibernateException;
	public Connection disconnect() throws HibernateException;
	public void reconnect() throws HibernateException;
	public void reconnect(Connection connection) throws HibernateException;
	public Connection close() throws HibernateException;
	public void cancelQuery() throws HibernateException;
	public boolean isOpen();
	public boolean isConnected();
	public boolean isDirty() throws HibernateException;
	public Serializable getIdentifier(Object object) throws HibernateException;
  public boolean contains(Object object);
  public void evict(Object object) throws HibernateException;
	public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException;
	public Object load(Class theClass, Serializable id) throws HibernateException;
	public void load(Object object, Serializable id) throws HibernateException;
	public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException;
	public Serializable save(Object object) throws HibernateException;
	public void save(Object object, Serializable id) throws HibernateException;
	public void saveOrUpdate(Object object) throws HibernateException;
	public void update(Object object) throws HibernateException;
	public void update(Object object, Serializable id) throws HibernateException;
	public Object saveOrUpdateCopy(Object object) throws HibernateException;
	public Object saveOrUpdateCopy(Object object, Serializable id) throws HibernateException;
	public void delete(Object object) throws HibernateException;
	public List find(String query) throws HibernateException;
	public List find(String query, Object value, Type type) throws HibernateException;
	public List find(String query, Object[] values, Type[] types) throws HibernateException;
	public Iterator iterate(String query) throws HibernateException;
	public Iterator iterate(String query, Object value, Type type) throws HibernateException;
	public Iterator iterate(String query, Object[] values, Type[] types) throws HibernateException;
	public Collection filter(Object collection, String filter) throws HibernateException;
	public Collection filter(Object collection, String filter, Object value, Type type) throws HibernateException;
	public Collection filter(Object collection, String filter, Object[] values, Type[] types) throws HibernateException;
	public int delete(String query) throws HibernateException;
	public int delete(String query, Object value, Type type) throws HibernateException;
	public int delete(String query, Object[] values, Type[] types) throws HibernateException;
	public void lock(Object object, LockMode lockMode) throws HibernateException;
	public void refresh(Object object) throws HibernateException;
	public void refresh(Object object, LockMode lockMode) throws HibernateException;
	public LockMode getCurrentLockMode(Object object) throws HibernateException;
	public Transaction beginTransaction() throws HibernateException;
	public Criteria createCriteria(Class persistentClass);
	public Query createQuery(String queryString) throws HibernateException;
	public Query createFilter(Object collection, String queryString) throws HibernateException;
	public Query getNamedQuery(String queryName) throws HibernateException;
	public Query createSQLQuery(String sql, String returnAlias, Class returnClass);
	public Query createSQLQuery(String sql, String[] returnAliases, Class[] returnClasses);
	public void clear();
	public Object get(Class clazz, Serializable id) throws HibernateException;
	public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException;
}