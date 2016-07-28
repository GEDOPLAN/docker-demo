# docker-demo
Demo für Docker und Einsatz von Docker in JavaEE Projekten

Für jedes Image existiert hier ein Verzeichnis mit einem Dockerfile. Es gibt ein Basis-Image (Abgleitet von Apline-Linux) und darauf aufbauende Images für Java, Wildfly und Mysql.
Das eigentliche Software Projekt ist in dem Ordner demo-project untergebracht. Es handelt sich um eine normale Webanwendung, welche als Maven-Projekt aufgesetzt wurde. Das Dockerfile des Projektes erzeugt einfach ein neues Image indem es dem Wildfly-Image das WAR hinzufügt. Ein Docker-Compose-File beschreibt die Container für Mysql und die Anwendung selbst.

Wie nun mit Docker gearbeitet werden kann, ist in dem Vagrant-File dokumentiert. Das File beinhaltet:
<ul>
<li>Installation von Docker</li>
<li>Starten von Docker</li>
<li>Starten einer eigenen Registry</li>
<li>Bauen der Images und pushen in Registry</li>
<li>Starten des Mysql-Containers (Danach Connecten und Datenbankeinrichten)</li>
<li>Stoppen des Mysql-Containers</li>
<li>Bauen des Projektes</li>
<li>Starten der Anwendung mit Docker-Compose</li>
</ul>

Für das Ausführen per Vagrant wird VirtualBox und Vagrant benötigt. Das Vagrant Plugin vagrant-vbguest muss ebenfalls installiert sein. Zum ausführen einfach in das Hauptverzeichnis wechseln (Wo das Vagrantfile liegt) und "vagrant up" ausführen.
