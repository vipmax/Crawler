package crawler

import java.util

import org.apache.mesos.Protos._
import org.apache.mesos.{Protos, Scheduler, SchedulerDriver}

import scala.collection.JavaConverters._


/**
 * Created by Max Petrov on 12.10.15.
 */
class CrawlerScheduler extends Scheduler {
  override def offerRescinded(schedulerDriver: SchedulerDriver, offerID: OfferID): Unit = {}

  override def disconnected(schedulerDriver: SchedulerDriver): Unit = {}

  override def reregistered(schedulerDriver: SchedulerDriver, masterInfo: MasterInfo): Unit = {}

  override def slaveLost(schedulerDriver: SchedulerDriver, slaveID: SlaveID): Unit = {}

  override def error(schedulerDriver: SchedulerDriver, s: String): Unit = {}

  override def statusUpdate(schedulerDriver: SchedulerDriver, taskStatus: TaskStatus): Unit = {
    println("Status update: task " + taskStatus.getTaskId.getValue + " state is " + taskStatus.getState)

    if (taskStatus.getState().equals(Protos.TaskState.TASK_FINISHED)) {
      System.out.println("Task " + taskStatus.getTaskId.getValue + " finished")
      schedulerDriver.stop()
    }
    else System.out.println("Task " + taskStatus.getTaskId.getValue + " has message " + taskStatus.getMessage)
  }

  override def frameworkMessage(schedulerDriver: SchedulerDriver, executorID: ExecutorID, slaveID: SlaveID, bytes: Array[Byte]): Unit = {}

  override def resourceOffers(schedulerDriver: SchedulerDriver, offers: util.List[Offer]): Unit = {
    for (offer <- offers.asScala) {
      val cpus = Resource.newBuilder.
        setType(org.apache.mesos.Protos.Value.Type.SCALAR)
        .setName("cpus")
        .setScalar(org.apache.mesos.Protos.Value.Scalar.newBuilder.setValue(4.0))
        .setRole("*")
        .build

      val id = "task" + System.currentTimeMillis()

      /*Very important*/
      val executorInfo = getCrawlerExecutor()

      val crawlerTask = TaskInfo.newBuilder
        .setName(id)
        .setTaskId(TaskID.newBuilder.setValue(id))
        .addResources(cpus)
        .setSlaveId(offer.getSlaveId)
        .setExecutor(executorInfo)
        .build

      schedulerDriver.launchTasks(List(offer.getId).asJava, List(crawlerTask).asJava)
    }
  }

  /*Very important
  * Mesos can execute only some command
  * It command must be run separately */
  def getCrawlerExecutor() = {
    val command = "java -cp /home/ubuntu/Desktop/Crawler/target/SNA-1.0.jar -Djava.library.path=/home/ubuntu/mesos-0.24.0/build/src/.libs crawler.CrawlerTaskExecutor"
    ExecutorInfo.newBuilder()
      .setCommand(CommandInfo.newBuilder().setValue(command))
      .setExecutorId(ExecutorID.newBuilder().setValue("1234"))
      .build()
  }

  override def registered(schedulerDriver: SchedulerDriver, frameworkID: FrameworkID, masterInfo: MasterInfo): Unit = {}

  override def executorLost(schedulerDriver: SchedulerDriver, executorID: ExecutorID, slaveID: SlaveID, i: Int): Unit = {}
}
