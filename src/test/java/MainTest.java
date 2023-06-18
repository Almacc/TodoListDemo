
import entity.list;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void setUp() {
        entityManagerFactory = jakarta.persistence.Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testAddTask() {
        String taskName = "Test Task";

        Main.addTask(entityManager, transaction, taskName);

        TypedQuery<list> query = entityManager.createQuery("SELECT l FROM list l WHERE l.task = :task", list.class);
        query.setParameter("task", taskName);
        list task = query.getSingleResult();

        assertNotNull(task);
        assertEquals(taskName, task.getTask());
    }

    @Test
    public void testShowTasks() {
        // Assuming there are some existing tasks in the database

        Main.showTasks(entityManager);
        // The output can be verified manually
    }

    @Test
    public void testDeleteTask() {
        int taskId = 1; // Assuming there is a task with ID 1 in the database

        Main.deleteTask(entityManager, transaction, taskId);

        list task = entityManager.find(list.class, taskId);
        assertNull(task);
    }
}
