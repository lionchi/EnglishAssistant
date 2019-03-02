package com.gavrilov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserRole.class)
public abstract class UserRole_ extends com.gavrilov.domain.Entity_ {

	public static volatile SingularAttribute<UserRole, String> rolename;
	public static volatile ListAttribute<UserRole, User> users;

	public static final String ROLENAME = "rolename";
	public static final String USERS = "users";

}

