grails.project.work.dir = 'target'
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.dependency.distribution = {
    remoteRepository(id: 'bintray', url: "https://api.bintray.com/maven/paulwoods/maven/customview") {
    }
}

grails.project.repos.default = "bintray"

grails.project.dependency.resolution = {

	inherits("global") {
	}

	log "warn"
	
	resolver = "maven"
	
	repositories {
		grailsCentral()
		mavenCentral()
	}

	dependencies {
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build(":release:2.2.1", ":rest-client-builder:1.0.3") {
			export = false
		}

		test ":code-coverage:1.2.6"

		test(":spock:0.7") {
			exclude "spock-grails-support"
		}

		compile ":codenarc:0.19"

		compile ":gmetrics:0.3.1"
	}
	
}
