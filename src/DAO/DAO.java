package DAO;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.C3p0Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class DAO<T> {
    private QueryRunner queryRunner = new QueryRunner(C3p0Utils.getDataSource());
    private Class<T> clazz;

    public DAO() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            if (typeArgs != null && typeArgs.length > 0) clazz = (Class<T>) typeArgs[0];
        }
    }

    public <E> E getForValue(String sql, Object... args) {
        try {
            return (E) queryRunner.query(sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> getForList(String sql, Object... args) {
        try {
            return queryRunner.query(sql, new BeanListHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public T get(String sql, Object... args) {
        try {
            return queryRunner.query(sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(String sql, Object... args) {
        try {
            queryRunner.update(sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
