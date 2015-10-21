# install requeriments for mesos
sudo apt-get -y install build-essential python-dev python-boto libcurl4-nss-dev libsasl2-dev libapr1-dev libsvn-dev libcunit1 libz-dev libssl-dev openssh-server maven openjdk-7-jdk

# fix hosts file on each node
sudo nano /etc/hosts
# should be smth like this 
"192.168.33.33   master"
"192.168.33.34   slave1"
"192.168.33.35   slave2"

# download and install mesos
sudo wget http://downloads.mesosphere.io/master/ubuntu/15.04/mesos_0.25.0-0.2.70.ubuntu1504_amd64.deb 
sudo chmod 777 mesos_0.25.0-0.2.70.ubuntu1504_amd64.deb 
sudo dpkg --install mesos_0.25.0-0.2.70.ubuntu1504_amd64.deb

# set up MESOS_LIBS and CRAWLER_DIR env vars
sudo echo "export MESOS_LIBS=/usr/local/lib" >> ~/.profile && export MESOS_LIB=/usr/local/lib
sudo echo "export CRAWLER_DIR=/media/sf_Crawler" >> ~/.profile && export CRAWLER_DIR=/media/sf_Crawler




# only on master
ssh-keygen -f ~/.ssh/id_rsa -P ""
ssh-copy-id -i ~/.ssh/id_rsa.pub vagrant@slave1
ssh-copy-id -i ~/.ssh/id_rsa.pub vagrant@slave2


on each node # drop all line and write "manual"
sudo nano /lib/systemd/system/mesos-master.service
sudo nano /lib/systemd/system/mesos-slave.service

# restart master
sudo fuser -k 5050/tcp 
sudo mesos-master --ip=192.168.33.33 --work_dir=/var/lib/mesos --log_dir=/var/log/mesos

# restart workers
sudo fuser -k 5051/tcp 
sudo mesos-slave --master=192.168.33.33:5050



java -cp $CRAWLER_DIR/target/SNA-1.0.jar -Djava.library.path=$MESOS_LIBS crawler.CrawlerMain 192.168.33.33
java -cp /media/sf_Crawler/target/SNA-1.0.jar -Djava.library.path=/usr/local/lib crawler.CrawlerMain 192.168.33.33