/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.taskmanager.Task;
import l2.gameserver.taskmanager.TaskTypes;

public class TaskManager.ExecutedTask
extends RunnableImpl {
    int _id;
    long _lastActivation;
    Task _task;
    TaskTypes _type;
    String[] _params;
    ScheduledFuture<?> _scheduled;

    public TaskManager.ExecutedTask(Task task, TaskTypes taskTypes, ResultSet resultSet) throws SQLException {
        this._task = task;
        this._type = taskTypes;
        this._id = resultSet.getInt("id");
        this._lastActivation = resultSet.getLong("last_activation") * 1000L;
        this._params = new String[]{resultSet.getString("param1"), resultSet.getString("param2"), resultSet.getString("param3")};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() throws Exception {
        this._task.onTimeElapsed(this);
        this._lastActivation = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_STATEMENTS[1]);
            preparedStatement.setLong(1, this._lastActivation / 1000L);
            preparedStatement.setInt(2, this._id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                _log.warn("cannot updated the Global Task " + this._id + ": " + sQLException.getMessage());
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        if (this._type == TaskTypes.TYPE_SHEDULED || this._type == TaskTypes.TYPE_TIME) {
            this.stopTask();
        }
    }

    public boolean equals(Object object) {
        return this._id == ((TaskManager.ExecutedTask)object)._id;
    }

    public Task getTask() {
        return this._task;
    }

    public TaskTypes getType() {
        return this._type;
    }

    public int getId() {
        return this._id;
    }

    public String[] getParams() {
        return this._params;
    }

    public long getLastActivation() {
        return this._lastActivation;
    }

    public void stopTask() {
        this._task.onDestroy();
        if (this._scheduled != null) {
            this._scheduled.cancel(false);
        }
        TaskManager.this._currentTasks.remove(this);
    }
}
