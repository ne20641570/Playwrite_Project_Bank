pipeline {
	agent any
	stages {
		stage('Checkout') {
			steps {
				echo 'Code checked out from GitHub'
			}
		}
		stage('Build') {
			steps {
				sh './mvnw clean compile'  // or your build command
			}
		}
		stage('Test') {
			steps {
				sh './mvnw test'          // run Playwright Java tests
			}
		}
	}
}
