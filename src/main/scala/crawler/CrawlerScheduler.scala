package crawler

import java.util
import java.util.concurrent.atomic.AtomicInteger

import org.apache.mesos.Protos._
import org.apache.mesos.{Protos, Scheduler, SchedulerDriver}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._


/**
 * Created by Max Petrov on 12.10.15.
 */
class CrawlerScheduler extends Scheduler {
  val logger = LoggerFactory.getLogger("CrawlerScheduler")

  val numOfAllTasks = 2
  val taskIDGenerator = new AtomicInteger()
  var numOfFinishedTasks = 0

  override def offerRescinded(schedulerDriver: SchedulerDriver, offerID: OfferID): Unit = {}

  override def disconnected(schedulerDriver: SchedulerDriver): Unit = {}

  override def reregistered(schedulerDriver: SchedulerDriver, masterInfo: MasterInfo): Unit = {}

  override def slaveLost(schedulerDriver: SchedulerDriver, slaveID: SlaveID): Unit = {}

  override def error(schedulerDriver: SchedulerDriver, s: String): Unit = {}

  override def statusUpdate(schedulerDriver: SchedulerDriver, taskStatus: TaskStatus): Unit = {

    logger.info(s"statusUpdate() task ${taskStatus.getTaskId.getValue} is in state ${taskStatus.getState}")

    if (taskStatus.getState.equals(TaskState.TASK_FINISHED)) {
      numOfFinishedTasks = numOfFinishedTasks + 1
      val executorResult = taskStatus.getData.toStringUtf8
      logger.info(s"executorResult = $executorResult")

    }

    if (numOfFinishedTasks >= numOfAllTasks) {
      logger.info("All tasks finished");
      schedulerDriver.stop()
    }
  }

  override def frameworkMessage(schedulerDriver: SchedulerDriver, executorID: ExecutorID, slaveID: SlaveID, bytes: Array[Byte]): Unit = {}


  override def resourceOffers(schedulerDriver: SchedulerDriver, offers: util.List[Offer]): Unit = {
    logger.info("resourceOffers() with {} offers", offers.size())

    for (offer <- offers.asScala) {
      val tasks: util.List[Protos.TaskInfo] = new util.ArrayList()

      // generate a unique task ID
      val taskId1 = Protos.TaskID.newBuilder().setValue(Integer.toString(taskIDGenerator.incrementAndGet())).build()
      val taskId2 = Protos.TaskID.newBuilder().setValue(Integer.toString(taskIDGenerator.incrementAndGet())).build()

      logger.info("Launching task {}", taskId1.getValue)
      logger.info("Launching task {}", taskId2.getValue)

      val cpus1 = Resource.newBuilder.
        setType(org.apache.mesos.Protos.Value.Type.SCALAR)
        .setName("cpus")
        .setScalar(org.apache.mesos.Protos.Value.Scalar.newBuilder.setValue(1.0))
        .setRole("*")
        .build
      val cpus2 = Resource.newBuilder.
        setType(org.apache.mesos.Protos.Value.Type.SCALAR)
        .setName("cpus")
        .setScalar(org.apache.mesos.Protos.Value.Scalar.newBuilder.setValue(1.0))
        .setRole("*")
        .build



      val crawlerTask1 = TaskInfo.newBuilder
        .setName("task " + taskId1.getValue)
        .setTaskId(taskId1)
        .addResources(cpus1)
        .setSlaveId(offer.getSlaveId)
        .setExecutor(getCrawlerExecutor("Hi"))
        .build

      val crawlerTask2 = TaskInfo.newBuilder
        .setName("task " + taskId2.getValue)
        .setTaskId(taskId2)
        .addResources(cpus2)
        .setSlaveId(offer.getSlaveId)
        .setExecutor(getCrawlerExecutor("hello"))
        .build

      tasks.add(crawlerTask1)
      tasks.add(crawlerTask2)

      val filters = Protos.Filters.newBuilder().setRefuseSeconds(1).build()
      schedulerDriver.launchTasks(offer.getId, tasks, filters)
    }
  }

  /*Very important
  * Mesos can execute only some command
  * It command must be run separately */
  def getCrawlerExecutor(messageForExecutor: String = ""): ExecutorInfo = {
    val command = "java -cp /home/ubuntu/Crawler/target/SNA-1.0.jar -Djava.library.path=/usr/local/lib crawler.CrawlerTaskExecutor "
    ExecutorInfo.newBuilder()
      .setCommand(CommandInfo.newBuilder().setValue(command + messageForExecutor))
      .setExecutorId(ExecutorID.newBuilder().setValue("" + System.nanoTime()))
      .build()
  }

  override def registered(schedulerDriver: SchedulerDriver, frameworkID: FrameworkID, masterInfo: MasterInfo): Unit = {}

  override def executorLost(schedulerDriver: SchedulerDriver, executorID: ExecutorID, slaveID: SlaveID, i: Int): Unit = {}
}
