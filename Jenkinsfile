pipeline {
	agent any

	environment {
		// Optional: set Java home if needed
		JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
		PATH = "${JAVA_HOME}/bin:${env.PATH}"
	}

	stages {
		stage('Checkout') {
			steps {
				echo 'Checking out source code...'
				checkout scm
			}
		}

		stage('Build') {
			steps {
				echo 'Building the Maven project...'
				sh 'mvn clean compile'
			}
		}

		stage('Install Playwright Browsers') {
			steps {
				echo 'Installing Playwright browsers...'
				sh 'npx playwright install --with-deps'
			}
		}

		stage('Run TestNG UI Tests') {
			steps {
				echo 'Running TestNG UI tests...'
				sh 'mvn test -DsuiteXmlFile=testng-ui.xml'
			}
		}

		stage('Run TestNG DB Tests') {
			steps {
				echo 'Running TestNG DB tests...'
				sh 'mvn test -DsuiteXmlFile=testng-db.xml'
			}
		}

		stage('Run TestNG API Tests') {
			steps {
				echo 'Running TestNG API tests...'
				sh 'mvn test -DsuiteXmlFile=testng-api.xml'
			}
		}

		stage('Archive Reports') {
			steps {
				echo 'Archiving test reports...'
				archiveArtifacts artifacts: 'reports/**/*.*', allowEmptyArchive: true
			}
		}
	}

	post {
		always {
			echo 'Pipeline finished!'
		}
	}
}
