package flink.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;
import org.hibernate.util.ArrayHelper;
import org.springframework.orm.hibernate3.HibernateCallback;

import flink.util.Pager;
import flink.util.Paginater;

/**
 *
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl extends RootDao implements BaseDao {
	/**
	 * for override.
	 * 
	 * @return
	 */
	protected abstract Class getModelClass();

	/**
	 * 清理session.
	 */
	@Override
	public void clear() {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void flush() {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.flush();
				return null;
			}
		});
	}

	@Override
	public void evict(final Object entity) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.evict(entity);
				return null;
			}
		});
	}

	@Override
	public void refresh(final Object entity) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.refresh(entity);
				return null;
			}
		});
	}

	@Override
	public void flushAndClear() {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public List findAll() {
		return findAll(getModelClass());
	}

	@Override
	public List findAll(Class clazz) {
		return this.getHibernateTemplate().loadAll(clazz);
	}

	@Override
	public List findAll(String orderProperty, String orderType) {
		HqlHelper helper = new HqlHelper(getModelClass());
		helper.orderBy(orderProperty, orderType);

		return getList(helper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.prca.biz.dao.BaseDao#deleteById(java.io.Serializable)
	 */
	@Override
	public void deleteById(Serializable id) {
		this.getHibernateTemplate().delete(this.findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.prca.biz.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flink.prca.persistence.dao.BaseDAO#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.prca.persistence.dao.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Object entity) {
		return this.getHibernateTemplate().save(entity);
	}

	@Override
	public Serializable save(Object entity, boolean flush, boolean clear) {
		Serializable pk = save(entity);

		if (flush) {
			flush();
		}

		if (clear) {
			clear();
		}

		return pk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.prca.persistence.dao.BaseDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Object entity) {
		this.getHibernateTemplate().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flink.prca.persistence.dao.BaseDAO#findById(java.io.Serializable)
	 */
	@Override
	public <E> E findById(Serializable id) {
		return findById(getModelClass(), id);
	}

	@Override
	public <E> E findByIdWithLock(Serializable id) {
		return findByIdWithLock(id, getModelClass());
	}

	@Override
	public <E> E findByIdWithLock(Serializable id, Class model) {
		return findByIdWithLock(id, model, true);
	}

	@Override
	public <E> E findByIdWithLock(Serializable id, boolean wait) {
		return findByIdWithLock(id, getModelClass(), wait);
	}

	@Override
	public <E> E findByIdWithLock(Serializable id, Class model, boolean wait) {
		if (id == null) {
			return null;
		}

		LockMode mode = wait ? LockMode.UPGRADE : LockMode.UPGRADE_NOWAIT;

		return (E) getHibernateTemplate().get(model, id, mode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flink.prca.persistence.dao.BaseDAO#findById(java.io.Serializable)
	 */
	@Override
	public <E> E findById(Class model, Serializable id) {
		if (id == null) {
			return null;
		}

		return (E) this.getHibernateTemplate().get(model, id);
	}

	/**
	 * description: 查询.
	 * 
	 * @param hql
	 * @param params
	 * @param paramTypes
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected List getList(final StringBuffer hql, final List params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql.toString())
						.setParameters(params.toArray(), getParamTypes(params))
						.list();
			}
		});
	}

	protected int execute(final String hql) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session.createQuery(hql).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int executeSql(final String sql) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session.createSQLQuery(sql).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int execute(final String hql, final Object param) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session.createQuery(hql)
								.setParameter(0, param).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int executeSql(final String sql, final Object param) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session.createSQLQuery(sql)
								.setParameter(0, param).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int execute(final String hql, final List params) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session
								.createQuery(hql)
								.setParameters(params.toArray(),
										getParamTypes(params)).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int execute(final HqlHelper hqlHelper) {
		return execute(hqlHelper.getHql().toString(), hqlHelper.getParamList());
	}

	protected int executeSql(final String sql, final List params) {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = session
								.createSQLQuery(sql)
								.setParameters(params.toArray(),
										getParamTypes(params)).executeUpdate();

						return Integer.valueOf(count);
					}
				})).intValue();
	}

	protected int executeSql(final HqlHelper hqlHelper) {
		return executeSql(hqlHelper.getHql().toString(),
				hqlHelper.getParamList());
	}

	/**
	 * 获取参数类型.
	 * 
	 * @param params
	 * @return
	 */
	private static Type[] getParamTypes(List params) {
		List paramTypes = new ArrayList();

		for (Iterator i = params.iterator(); i.hasNext();) {
			Object param = i.next();
			paramTypes.add(TypeFactory
					.heuristicType(param.getClass().getName()));
		}

		return ArrayHelper.toTypeArray(paramTypes);
	}

	/**
	 * description: 查询.
	 * 
	 * @param hql
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected List getList(final String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	protected List getList(final String hql, Object param) {
		return this.getHibernateTemplate().find(hql, param);
	}

	/**
	 * description: 查询.
	 * 
	 * @param helper
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected List getList(final HqlHelper helper) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.list(session);
			}
		});
	}

	/**
	 * 加锁查询.
	 * 
	 * @param helper
	 * @param alias
	 * @param upgrade
	 * @return
	 */
	protected List getList(final QueryHelper helper, final String alias,
			final LockMode lockMode) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.list(session, alias, lockMode);
			}
		});
	}

	/**
	 * 加锁查询.
	 * 
	 * @param helper
	 * @param alias
	 * @param upgrade
	 * @return
	 */
	protected Object getUniqueResult(final QueryHelper helper,
			final String alias, final LockMode lockMode) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResult(session, alias, lockMode);
			}
		});
	}

	/**
	 * description: 查询.
	 * 
	 * @param hql
	 * @param params
	 * @param paramTypes
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected Object getUniqueResult(final StringBuffer hql, final List params) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql.toString())
						.setParameters(params.toArray(), getParamTypes(params))
						.uniqueResult();
			}
		});
	}

	/**
	 * description: 查询.
	 * 
	 * @param hql
	 * @param params
	 * @param paramTypes
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected Object getUniqueResult(final QueryHelper helper) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResult(session);
			}
		});
	}

	@Override
	public Object getUniqueResult(Class clazz, String property, Object value) {
		final HqlHelper helper = new HqlHelper(clazz).append("where "
				+ property + " = ?", value);
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResult(session);
			}
		});
	}

	protected Object getUniqueResult(String property, Object value) {
		final HqlHelper helper = new HqlHelper(getModelClass()).append("where "
				+ property + " = ?", value);
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResult(session);
			}
		});
	}

	@Override
	public List<?> getResults(String property, Object value) {
		final HqlHelper helper = new HqlHelper(getModelClass()).append("where "
				+ property + " = ?", value);
		return (List<?>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return helper.list(session);
					}
				});
	}

	protected Object getUniqueResult(final StringBuffer hql, Serializable param) {
		List params = new ArrayList();
		params.add(param);

		return getUniqueResult(hql, params);
	}

	/**
	 * description: 查询.
	 * 
	 * @param hql
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected Object getUniqueResult(final String hql) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).uniqueResult();
			}
		});
	}

	protected Object getUniqueResult(DetachedCriteria criteria) {
		List list = this.getHibernateTemplate().findByCriteria(criteria, 0, 1);

		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param hql
	 * @param page
	 * @return
	 */
	protected Paginater getPageData(final String hql, final Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append(hql);

		return this.getPageData(helper, pager);
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageData(final QueryHelper helper, final Pager pager) {
		if (pager == null) {
			List list = getList(helper);
			int size = list.size();
			Paginater p = new Paginater(size, 1, size == 0 ? 1 : size);
			p.setData(list);

			return p;
		} else {
			return this.getPageData(helper, pager.getPageNumber(),
					pager.getPageSize());
		}
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageData(final String hql, final long pageNumber,
			final int pageSize) {
		QueryHelper helper = new QueryHelper();
		helper.append(hql);

		return this.getPageData(helper, pageNumber, pageSize);
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageData(final QueryHelper helper,
			final long pageNumber, final int pageSize) {
		return (Paginater) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Paginater paginater = new Paginater(helper
								.getRecordCount(session), pageNumber, pageSize);

						List list = helper
								.getQuery(session)
								.setMaxResults(paginater.getPageSize())
								.setFirstResult(
										(int) paginater.getOffsetIndex())
								.list();
						paginater.setData(list);

						return paginater;
					}
				});
	}

	/**
	 * description: 查询.
	 * 
	 * @param helper
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected List getListBySql(final QueryHelper helper) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return helper.listBySql(session);
					}
				});
	}

	protected List getListBySql(final QueryHelper helper,
			final ResultTransformer transformer) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return helper.listBySql(session, transformer);
					}
				});
	}

	protected List getListBySql(final String sql) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						QueryHelper helper = new QueryHelper();
						helper.append(sql);

						return helper.listBySql(session);
					}
				});
	}

	/**
	 * description: 查询.
	 * 
	 * @param helper
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected Object getUniqueResultBySql(final QueryHelper helper) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResultBySql(session);
			}
		});
	}

	/**
	 * description: 查询.
	 * 
	 * @param helper
	 * @return
	 * 
	 * 
	 * @since 2007-12-03
	 */
	protected Object getUniqueResultBySql(final String sql) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				QueryHelper helper = new QueryHelper();
				helper.append(sql);

				return helper.getUniqueResultBySql(session);
			}
		});
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageDataBySql(final QueryHelper helper,
			final Paginater paginater) {
		return (Paginater) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List list = helper
								.getSQLQuery(session)
								.setMaxResults(paginater.getPageSize())
								.setFirstResult(
										(int) paginater.getOffsetIndex())
								.list();
						paginater.setData(list);

						return paginater;
					}
				});
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageDataBySql(final QueryHelper helper,
			final Pager pager) {
		return (Paginater) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Paginater paginater = new Paginater(helper
								.getRecordCountBySql(session), pager
								.getPageNumber(), pager.getPageSize());

						List list = helper
								.getSQLQuery(session)
								.setMaxResults(paginater.getPageSize())
								.setFirstResult(
										(int) paginater.getOffsetIndex())
								.list();
						paginater.setData(list);

						return paginater;
					}
				});
	}

	/**
	 * 获取一页数据.
	 * 
	 * @param helper
	 * @param page
	 * @return
	 */
	protected Paginater getPageDataBySql(final QueryHelper helper,
			final Pager pager, final ResultTransformer transformer) {
		return (Paginater) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						if (pager == null) {
							List list = getListBySql(helper, transformer);
							int size = list.size();
							Paginater p = new Paginater(size, 1, size == 0 ? 1
									: size);
							p.setData(list);

							return p;
						}

						Paginater paginater = new Paginater(helper
								.getRecordCountBySql(session), pager
								.getPageNumber(), pager.getPageSize());

						List list = helper
								.getSQLQuery(session)
								.setMaxResults(paginater.getPageSize())
								.setFirstResult(
										(int) paginater.getOffsetIndex())
								.setResultTransformer(transformer).list();
						paginater.setData(list);

						return paginater;
					}
				});
	}

	/**
	 * 取实体映射属性
	 * 
	 * @return
	 * 
	 */
	protected String[] getProperties() {
		return getProperties(this.getModelClass());
	}

	protected String[] getProperties(Class theClass) {
		if (theClass == null) {
			return new String[0];
		}

		String className = theClass.getName();
		ClassMetadata meta = this.getSessionFactory().getClassMetadata(
				className);

		return meta == null ? new String[0] : meta.getPropertyNames();
	}

	/**
	 * 获取字符串数组条件对应的hql字符串
	 * 
	 * @param listOfStr
	 * @return
	 */
	public String strsCondition(List<String> listOfStr) {
		if (listOfStr == null) {
			return null;
		}
		return this
				.strsCondition(listOfStr.toArray(new String[listOfStr.size()]));
	}

	/**
	 * 获取字符串数组条件对应的hql字符串
	 * 
	 * @param arrOfStr
	 * @return
	 */
	public String strsCondition(String[] arrOfStr) {
		if (arrOfStr == null || arrOfStr.length == 0) {
			return null;
		}
		String result = "";
		for (String s : arrOfStr) {
			result = result + ",'" + s + "'";
		}
		return result.substring(1);
	}

	@Override
	public void lock(Object entity, LockMode lockMode) {
		this.getHibernateTemplate().lock(entity, lockMode);
	}

	private static QueryHelper getQueryHelper(Class<?> clazz,
			Map<String, Object> map, String idKey, Object idValue) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(*) as count) from");
		helper.append(clazz.getName());
		helper.append("where 1=1");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				helper.append("and").append(key).append("= ?", value);
			}
		}
		if (StringUtils.isNotEmpty(idKey)) {
			helper.append("and").append(idKey).append("<> ?", idValue);

		}
		return helper;
	}

	@Override
	public Long findCountByParam(Class<?> clazz, Map<String, Object> params,
			String idKey, Object idValue) {
		QueryHelper helper = getQueryHelper(clazz, params, idKey, idValue);
		Object obj = getUniqueResult(helper);
		Map<String, Object> map = (Map<String, Object>) obj;
		return (Long) map.get("count");
	}

	@Override
	public Object getUniqueResult(Class clazz, Map<String, Object> params) {
		final HqlHelper helper = new HqlHelper(clazz);
		helper.append("where 1=1");
		for (Map.Entry entry : params.entrySet()) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			helper.append("and " + key + " = ?", value);
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return helper.getUniqueResult(session);
			}
		});
	}
}
