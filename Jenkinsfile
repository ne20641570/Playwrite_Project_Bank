pipeline {
	agent any  // Runs on any available Jenkins node

	tools {
		maven 'Maven-3.9'  // Make sure Maven is installed and configured in Jenkins
		jdk 'JDK-17'       // Make sure your Java version matches Playwright requirements
	}

	parameters {
		choice(name: 'SUITE_FILE', choices: ['testng-ui.xml', 'testng-api.xml', 'testng-db.xml'], description: 'Select TestNG suite XML')
		string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
		string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
		string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')
		choice(name: 'BROWSER', choices: ['chromium', 'webkit'], description: 'Override browser')
		string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
	}

	environment {
		REPORT_DIR = "reports/extentReports/${new Date().format('yyyy-MM-dd')}"
	}

	stages {
		stage('Checkout') {
			steps {
				echo "Checking out source code..."
				git branch: 'main',
				url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
				credentialsId: 'github-creds'
			}
		}

		stage('Run Playwright Tests') {
			steps {
				script {
					def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

					if (params.TEST_CLASS?.trim()) { mvnCmd += " -Dtest=${params.TEST_CLASS}" }
					if (params.GROUPS?.trim()) { mvnCmd += " -Dgroups=${params.GROUPS}" }
					if (params.BROWSER?.trim()) { mvnCmd += " -Dbrowser=${params.BROWSER}" }
					if (params.THREAD_COUNT?.trim()) { mvnCmd += " -Dthread.count=${params.THREAD_COUNT}" }

					echo "Running Maven command: ${mvnCmd}"
					sh mvnCmd
				}
			}
		}
	}

	post {
		always {
			echo "Archiving reports..."
			archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*.html", allowEmptyArchive: true
		}
	}
}

