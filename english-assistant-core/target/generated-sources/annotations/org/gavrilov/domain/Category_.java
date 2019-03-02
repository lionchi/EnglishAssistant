package org.gavrilov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends org.gavrilov.domain.Entity_ {

	public static volatile ListAttribute<Category, World> worlds;
	public static volatile SingularAttribute<Category, String> nameCategory;

	public static final String WORLDS = "worlds";
	public static final String NAME_CATEGORY = "nameCategory";

}

