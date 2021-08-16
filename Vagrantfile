# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"

  # run bootstap script to install needed tools &
  config.vm.provision :shell, :path => "resources/vagrant/bootstrap.sh"

  # localhost:3000 -> vagrant:3000
  config.vm.network :forwarded_port, host: 3000, guest: 3000

  # postresql (for future use)
  # config.vm.network :forwarded_port, host: 5984, guest: 5984

  config.vm.network "forwarded_port", guest: 5432, host: 5432

  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "playbook.yml"
  end

end
