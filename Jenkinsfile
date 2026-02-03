pipeline {
	agent any

	tools {
		maven 'Maven-3.9'
	}

	parameters {
		choice(name: 'SUITE_FILE',
			choices: ['testng-ui.xml', 'testng-api.xml', 'testng-db.xml'],
			description: 'Select TestNG suite XML')

		string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
		string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
		string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')

		choice(name: 'BROWSER',
			choices: ['all', 'chromium', 'webkit'],
			description: 'Override browser')

		string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
	}

	environment {
		REPORT_DIR = ""
		EMAIL_RECIPIENTS = "your_email@example.com"
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
				sh 'mvn clean compile'
			}
		}

		stage('Run Tests') {
			steps {
				script {

					// ---------------- BUILD MAVEN COMMAND ----------------
					def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

					if (params.GROUPS?.trim()) {
						mvnCmd += " -Dgroups=${params.GROUPS}"
					}
					if (params.TEST_CLASS?.trim()) {
						mvnCmd += " -Dtest=${params.TEST_CLASS}"
					}
					if (params.TEST_METHOD?.trim()) {
						mvnCmd += " -Dtest=${params.TEST_METHOD}"
					}
					if (params.BROWSER && params.BROWSER != 'ALL') {
						mvnCmd += " -Dbrowser=${params.BROWSER}"
					}
					if (params.THREAD_COUNT?.trim()) {
						mvnCmd += " -Dthread.count=${params.THREAD_COUNT}"
					}

					echo "================================="
					echo "Running command:"
					echo mvnCmd
					echo "================================="

					// ⭐ KEY FIX: Allow pipeline to continue even if tests fail
					catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
						sh mvnCmd
					}

					// ---------------- EXTENT REPORT HANDLING ----------------
					def reportDate = new Date().format('yyyy-MM-dd')
					def reportBaseDir = "reports/extentReports/${reportDate}"

					def reportFile = ""
					if (params.SUITE_FILE == 'testng-ui.xml') {
						reportFile = "Automation Playwright Suite.html"
					} else if (params.SUITE_FILE == 'testng-api.xml') {
						reportFile = "API RestAssured Suite.html"
					} else if (params.SUITE_FILE == 'testng-db.xml') {
						reportFile = "DataBase Suite.html"
					}

					sh """
                        echo "Preparing Extent report for Jenkins..."
                        cd ${reportBaseDir}
                        ls -l
                        cp "${reportFile}" index.html
                    """

					env.REPORT_DIR = reportBaseDir
				}
			}
		}
	}

	post {
		always {

			// ⭐ LEFT BANNER HTML REPORT
			publishHTML(target: [
				reportName: "ExtentReport_${params.SUITE_FILE}",
				reportDir: "${env.REPORT_DIR}",
				reportFiles: "index.html",
				keepAll: true,
				alwaysLinkToLastBuild: true,
				allowMissing: false
			])

			// ⭐ ARCHIVE REPORT FILES
			archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*.html",
			allowEmptyArchive: true

			echo "================================="
			echo "Extent Report URL:"
			echo "${env.BUILD_URL}ExtentReport_${params.SUITE_FILE}/"
			echo "================================="
		}
	}
}
