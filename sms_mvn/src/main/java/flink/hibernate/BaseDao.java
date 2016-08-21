package flink.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;

/**
 * Title: BaseDAO
 * Description: 
 * 
 */
public interface BaseDao {
	
	/**
	 * description: 保存对象.
	 * 
	 * @param object
	 * @return
	 * 
	 * 
	 * @since 2007-11-25
	 */
	Serializable save(Object entity);
	Serializable save(Object entity, boolean flush, boolean clear);
	
	/**
	 * description: 保存对象.
	 * 
	 * @param object
	 * @return
	 * 
	 * 
	 * @since 2007-11-25
	 */
	void saveOrUpdate(Object entity);

	/**
	 * description: 更新对象.
	 * 
	 * @param object
	 * @return
	 * 
	 * 
	 * @since 2007-11-26
	 */
	void update(Object entity);

	/**
	 * description: 删除对象.
	 * 
	 * @param object
	 * @return
	 * 
	 * 
	 * @since 2007-11-26
	 */
	void delete(Object entity);
	
	/**
	 * description: 删除对象.
	 * 
	 * @param id
	 * @return
	 * 
	 * 
	 * @since 2007-11-26
	 */
	void deleteById(Serializable id);
	
	/**
	 * description: 查找对象.
	 * 
	 * @param id
	 * @return
	 * 
	 * 
	 * @since 2007-11-27
	 */
	<E> E findById(Serializable id);
	<E> E findById(Class model, Serializable id);
	
	/**
	 * 查询并加锁.
	 * 
	 * @param id
	 * @return
	 */
	<E> E findByIdWithLock(Serializable id);
	
	/**
	 * 查询并加锁.
	 * 
	 * @param id
	 * @return
	 */
	<E> E findByIdWithLock(Serializable id, Class model);
	
	/**
	 * 查询并加锁.
	 * 
	 * @param id
	 * @param wait 是否等待, 不等待情况下如果不能锁定数据则返回空值.
	 * @return
	 */
	<E> E findByIdWithLock(Serializable id, boolean wait);
	
	/**
	 * 查询并加锁.
	 * 
	 * @param id
	 * @return
	 */
	<E> E findByIdWithLock(Serializable id, Class model, boolean wait);
	
	/**
	 * description: 查找对象.
	 * 
	 * @return
	 * 
	 * 
	 * @since 2007-11-27
	 */
	List findAll();
	
	List findAll(Class clazz);
	
	List findAll(String orderProperty, String orderType);
	
	/**
	 * 清理缓存.
	 */
	void clear();
	void flush();
	void flushAndClear();
	void refresh(Object entity);
	void evict(Object entity);
	
	void lock(Object entity, LockMode lockMode);
	
	Long findCountByParam(Class<?> clazz, Map<String, Object> params, String idKey, Object idValue);
	
	Object getUniqueResult(Class clazz, String property, Object value);
	
	Object getUniqueResult(Class clazz, Map<String, Object> params);
	
	List<?> getResults(String property, Object value);
}
