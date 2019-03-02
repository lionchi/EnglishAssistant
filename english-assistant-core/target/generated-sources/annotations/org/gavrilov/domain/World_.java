package org.gavrilov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(World.class)
public abstract class World_ extends org.gavrilov.domain.Entity_ {

	public static volatile SingularAttribute<World, String> translation;
	public static volatile SingularAttribute<World, Category> category;
	public static volatile SingularAttribute<World, String> value;
	public static volatile SingularAttribute<World, User> user;

	public static final String TRANSLATION = "translation";
	public static final String CATEGORY = "category";
	public static final String VALUE = "value";
	public static final String USER = "user";

}

