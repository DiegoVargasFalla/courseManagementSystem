package uni.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import uni.models.Course;
import uni.models.Note;
import uni.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppRepository {

    public List<Course> getCourses() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c", Course.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            System.out.println("-> Connection with data base is closed, getCourses");
            em.close();
        }
        return new ArrayList<>();
    }

    public Optional<Course> getCourse(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            return Optional.of(em.find(Course.class, id));
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            System.out.println("-> Connection with data base is closed, getCourse");
            em.close();
        }
        return Optional.empty();
    }

    public boolean createCourse(Course course) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error -> " + e.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            if(em.isOpen()) {
                System.out.println("-> Connection with data base is closed, createCourse");
                em.close();
            }
        }
        return true;
    }

    public boolean updateCourse(Course course) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            System.out.println("Error -> " + cause.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            System.out.println("-> Connection with data base is closed, UpdateCourse");
            em.close();
        }
        return true;
    }

    public boolean createStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            em.close();
        }
        return true;
    }

    public ArrayList<Student> getStudents() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return (ArrayList<Student>) query.getResultList();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

    public Optional<Student> getStudent(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            return Optional.of(em.find(Student.class, id));
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            System.out.println("-> Connection with data base is closed, getStudent");
            em.close();
        }
        return Optional.empty();
    }

    public boolean updateStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Student studentFound = em.find(Student.class, student.getId());
            em.getTransaction().begin();

            if(student.getName() != null) {
                studentFound.setName(student.getName());
            } if(student.getLastName() != null) {
                studentFound.setLastName(student.getLastName());
            } if(student.getDni() != null) {
                studentFound.setDni(student.getDni());
            } if(student.getNumSemester() != 0) {
                studentFound.setNumSemester(student.getNumSemester());
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public boolean deleteStudent(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(student);
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            em.close();
        }
        return true;
    }

    // note's operations
    public boolean createNote(Note note) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            System.out.println("-> Connection with data base is closed, createNote");
            em.close();
        }
        return true;
    }

    public boolean checkExistStudent(Long dni) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.dni = :dni", Long.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
            System.err.println("-> count: " + count);
            if(count > 0) {
                return false;
            };
        } catch (Exception e) {
            System.err.println("-> Ocurrio un error");
            System.err.println(e.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        System.err.println("-> No existe el estudiante");
        return true;
    }
}
