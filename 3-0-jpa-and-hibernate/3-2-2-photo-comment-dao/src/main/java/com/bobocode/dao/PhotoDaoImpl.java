package com.bobocode.dao;

import com.bobocode.model.Photo;
import com.bobocode.model.PhotoComment;
import com.bobocode.util.ExerciseNotCompletedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Please note that you should not use auto-commit mode for your implementation.
 */
public class PhotoDaoImpl implements PhotoDao {
    private EntityManagerFactory entityManagerFactory;

    public PhotoDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Photo photo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(photo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Photo findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Photo photo = entityManager.createQuery("select p from Photo p where p.id = :id", Photo.class)
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return photo;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Photo> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            List<Photo> photos = entityManager.createQuery("select p from Photo p", Photo.class)
                    .getResultList();
            entityManager.getTransaction().commit();
            return photos;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(Photo photo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Photo managedPhoto = entityManager.merge(photo);
            entityManager.remove(managedPhoto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void addComment(long photoId, String comment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Photo photo = entityManager.getReference(Photo.class, photoId);
            PhotoComment photoComment = new PhotoComment();
            photoComment.setCreatedOn(LocalDateTime.now());
            photoComment.setText(comment);
            photoComment.setPhoto(photo);
            photo.addComment(photoComment);
            entityManager.persist(photo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }
}
