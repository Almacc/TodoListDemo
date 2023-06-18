import entity.list;
import jakarta.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();





        try {
            Scanner scanner = new Scanner(System.in);
            int choice = 0;

            while (choice != 4) {
                System.out.println("1. Add task");
                System.out.println("2. Show tasks");
                System.out.println("3. Delete task");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter task name: ");
                        String taskName = scanner.nextLine();
                        addTask(entityManager, transaction, taskName);
                        break;
                    case 2:
                        showTasks(entityManager);
                        break;
                    case 3:
                        System.out.print("Enter the ID of the task to delete: ");
                        int taskId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        deleteTask(entityManager, transaction, taskId);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                        break;
                }

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static void addTask(EntityManager entityManager, EntityTransaction transaction, String taskName) {
        transaction.begin();

        try {
            list task = new list();
            task.setTask(taskName);

            entityManager.persist(task);
            transaction.commit();

            System.out.println("Task added successfully!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void showTasks(EntityManager entityManager) {
        TypedQuery<list> query = entityManager.createQuery("SELECT l FROM list l", list.class);
        List<list> tasks = query.getResultList();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Tasks:");
            for (list task : tasks) {
                System.out.println(task.getIdlist() + ". " + task.getTask());
            }
        }
    }

    public static void deleteTask(EntityManager entityManager, EntityTransaction transaction, int taskId) {
        transaction.begin();

        try {
            list task = entityManager.find(list.class, taskId);
            if (task != null) {
                entityManager.remove(task);
                transaction.commit();
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Task not found with ID: " + taskId);
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
