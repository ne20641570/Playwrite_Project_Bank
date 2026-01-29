pipeline {
	agent any

	environment {
		MAVEN_HOME = '/usr/local/opt/maven'  // adjust if Maven is installed elsewhere
		PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
	}

	stages {
		stage('Checkout') {
			steps {
				echo 'Checking out code from GitHub...'
				checkout scm
			}
		}

		stage('Build Project') {
			steps {
				echo 'Building the Java project with Maven...'
				sh 'mvn clean compile'
			}
		}

		stage('Install Playwright Browsers') {
			steps {
				echo 'Installing Playwright browsers...'
				// If Node is installed via Homebrew or elsewhere, adjust PATH if needed
				sh 'npx playwright install --with-deps'
			}
		}

		stage('Run Playwright Tests') {
			steps {
				echo 'Running Playwright Java tests...'
				sh 'mvn test'
			}
		}

		stage('Archive Test Reports') {
			steps {
				echo 'Archiving reports...'
				archiveArtifacts artifacts: 'reports/**/*.*', allowEmptyArchive: true
			}
		}
	}

	post {
		always {
			echo 'Cleaning workspace after build...'
			cleanWs()
		}
		success {
			echo 'Pipeline completed successfully!'
		}
		failure {
			echo 'Pipeline failed. Check the logs.'
		}
	}
}
