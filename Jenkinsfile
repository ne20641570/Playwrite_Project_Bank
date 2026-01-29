pipeline {
	agent any

	environment {
		// Path to your JDK and Maven if needed, adjust if Jenkins uses global tools
		JAVA_HOME = '/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home'
		MAVEN_HOME = '/usr/local/apache-maven'
		PATH = "${env.MAVEN_HOME}/bin:${env.JAVA_HOME}/bin:${env.PATH}"
	}

	stages {
		stage('Checkout') {
			steps {
				git branch: 'main',
				url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
				credentialsId: 'github-creds'
			}
		}

		stage('Build') {
			steps {
				echo 'Building the project with Maven...'
				sh 'mvn clean compile'
			}
		}

		stage('Install Playwright Browsers') {
			steps {
				echo 'Installing Playwright browsers...'
				// Only needed once or if browser updates are required
				sh 'mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"'
			}
		}

		stage('Run Tests') {
			steps {
				echo 'Running Playwright tests...'
				sh 'mvn test'
			}
		}

		stage('Archive Results') {
			steps {
				echo 'Archiving test results...'
				junit '**/target/surefire-reports/*.xml'
			}
		}
	}

	post {
		always {
			echo 'Pipeline finished.'
		}
		success {
			echo 'All stages completed successfully!'
		}
		failure {
			echo 'Something went wrong. Check logs.'
		}
	}
}
