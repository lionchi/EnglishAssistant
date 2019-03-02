package com.gavrilov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.gavrilov.domain.Entity_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, World> worlds;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, UserRole> userRole;
	public static volatile SingularAttribute<User, String> fio;
	public static volatile SingularAttribute<User, Integer> enabled;

	public static final String PASSWORD = "password";
	public static final String WORLDS = "worlds";
	public static final String LOGIN = "login";
	public static final String USER_ROLE = "userRole";
	public static final String FIO = "fio";
	public static final String ENABLED = "enabled";

}

