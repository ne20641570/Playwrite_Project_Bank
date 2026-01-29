pipeline {
	agent any

	stages {
		stage('Checkout') {
			steps {
				echo 'Code checked out successfully'
			}
		}

		stage('Build') {
			steps {
				echo 'Compiling project with Maven'
				sh 'mvn clean compile'
			}
		}

		stage('Install Browsers') {
			steps {
				echo 'Installing Playwright browsers'
				sh './node_modules/.bin/playwright install --with-deps || echo "Skipping if already installed"'
			}
		}

		stage('Run Tests') {
			steps {
				echo 'Running Playwright Java tests'
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
