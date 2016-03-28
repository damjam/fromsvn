package flink.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

/**
 * hibernate
 * 
 * 
 */
public class QueryHelper extends HqlHelper {

	public List listBySql(Session session) {
		return getSQLQuery(session).list();
	}

	public List listBySql(Session session, ResultTransformer transformer) {
		return getSQLQuery(session).setResultTransformer(transformer).list();
	}

	public Object getUniqueResultBySql(Session session) {
		return this.getSQLQuery(session).uniqueResult();
	}
}
