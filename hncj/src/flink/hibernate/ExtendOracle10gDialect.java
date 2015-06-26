package flink.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

public class ExtendOracle10gDialect extends Oracle10gDialect {

	public ExtendOracle10gDialect() {
		super();
		registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
	}
}
