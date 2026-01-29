pipeline {
	agent any

	stages {
		stage('Checkout') {
			steps {
				echo 'Checking out code...'
				checkout scm
			}
		}

		stage('Build') {
			steps {
				echo 'Building project...'
				sh 'mvn clean compile'
			}
		}

		stage('Run Tests') {
			steps {
				echo 'Running all TestNG tests...'
				sh 'mvn test'
			}
		}

		stage('Archive Reports') {
			steps {
				archiveArtifacts artifacts: 'reports/**/*.*', allowEmptyArchive: true
			}
		}
	}
}
