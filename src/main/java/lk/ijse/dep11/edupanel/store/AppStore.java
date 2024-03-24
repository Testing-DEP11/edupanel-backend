package lk.ijse.dep11.edupanel.store;

import com.google.cloud.storage.Bucket;

import javax.persistence.EntityManager;

public class AppStore {
    private static final ThreadLocal<EntityManager> emStore =  new ThreadLocal<>();
    private static final ThreadLocal<Bucket> bucketStore =  new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        return emStore.get();
    }

    public static Bucket getBucket() {
        return bucketStore.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        emStore.set(entityManager);
    }

    public static void setBucket(Bucket bucket) {
        bucketStore.set(bucket);
    }
}
