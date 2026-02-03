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
			choices: ['chromium', 'webkit'],
			description: 'Override browser')

		string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
	}

	environment {
		REPORT_DIR = "reports/extentReports/${new Date().format('yyyy-MM-dd')}"
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
					if (params.BROWSER?.trim()) {
						mvnCmd += " -Dbrowser=${params.BROWSER}"
					}
					if (params.THREAD_COUNT?.trim()) {
						mvnCmd += " -Dthread.count=${params.THREAD_COUNT}"
					}

					echo "================================="
					echo "Running command:"
					echo mvnCmd
					echo "================================="

					catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
						sh mvnCmd
					}
				}

				// ✅ EXTENT REPORT HANDLING (VERY IMPORTANT)
				script {
					def reportDate = new Date().format('yyyy-MM-dd')
					def reportBaseDir = "reports/extentReports/${reportDate}"
					def videoSourceDir = "reports/videos/${reportDate}/video"
					def videoTargetDir = "${reportBaseDir}/videos"

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
						mkdir -p ${reportBaseDir}
						cp "${reportBaseDir}/${reportFile}" ${reportBaseDir}/index.html || true

						echo "Copying Playwright failure videos..."
						if [ -d "${videoSourceDir}" ]; then
							mkdir -p ${videoTargetDir}
							cp -R ${videoSourceDir}/* ${videoTargetDir}/ || true
						else
							echo "No videos found (tests may have passed)"
						fi
					"""

					env.REPORT_DIR = reportBaseDir
				}
			}
		}
	}

	post {
		always {
			publishHTML(target: [
				reportName: "ExtentReport_${params.SUITE_FILE}",
				reportDir: "${env.REPORT_DIR}",
				reportFiles: "index.html",
				keepAll: true,
				alwaysLinkToLastBuild: true,
				allowMissing: true
			])

			archiveArtifacts artifacts: """
				${env.REPORT_DIR}/**/*.html,
				${env.REPORT_DIR}/videos/**/*
				""",
			allowEmptyArchive: true
			// -------- Build Summary Links --------
			script {
				def reportUrl = "index.html"
				def videoUrl  = "${videoSourceDir}/*"

				currentBuild.description = """
                <b>Extent Report:</b>
                <a href='${reportUrl}' target='_blank'>Open Report</a><br/>
                <b>Failure Videos:</b>
                <a href='${videoUrl}' target='_blank'>Open Videos</a>
                """
			}
			//echo "Extent Report URL:"
			//echo "${env.BUILD_URL}Extent_Report_${params.SUITE_FILE}/"
		}
	}
}
