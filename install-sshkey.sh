#!/bin/sh

if [ ! -f ~/.ssh/id_rsa_vagrant.pub ]; then
    cp /home/vagrant/project/sshkey/id_rsa.pub /home/vagrant/.ssh/id_rsa_vagrant.pub;
    cat /home/vagrant/project/sshkey/id_rsa.pub >> /home/vagrant/.ssh/authorized_keys
    echo 'The key from sshkey has been succesfully installed'
else
    echo 'The key from sshkey is already installed'
fi

