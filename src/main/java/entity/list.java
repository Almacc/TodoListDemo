package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "list", schema = "todolist", catalog = "")
public class list {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idlist")
    private int idlist;
    @Basic
    @Column(name = "task")
    private String task;

    public int getIdlist() {
        return idlist;
    }

    public void setIdlist(int idlist) {
        this.idlist = idlist;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        list that = (list) o;

        if (idlist != that.idlist) return false;
        if (task != null ? !task.equals(that.task) : that.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idlist;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }
}
