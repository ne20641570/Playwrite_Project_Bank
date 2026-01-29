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
				sh './mvnw clean compile' // Or your build command
			}
		}

		stage('Test') {
			steps {
				sh './mvnw test'          // Run Playwright Java tests
			}
		}
	}

	post {
		always {
			echo 'Pipeline finished'
		}
	}
}
