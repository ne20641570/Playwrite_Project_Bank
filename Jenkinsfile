pipeline {
	agent any

	tools {
		// Use Maven installed in Jenkins global tool configuration
		maven 'Maven-3.9'
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

		stage('Setup Maven') {
			steps {
				echo 'Checking Maven installation...'
				script {
					def mvnExists = sh(script: 'which mvn || true', returnStdout: true).trim()
					if (!mvnExists) {
						echo 'Maven not found. Installing via Homebrew...'
						sh '''
                            # Install Homebrew if missing
                            if ! command -v brew >/dev/null 2>&1; then
                                /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
                            fi
                            brew update
                            brew install maven
                        '''
					} else {
						echo "Maven found at ${mvnExists}"
					}
				}
			}
		}

		stage('Checkout') {
			steps {
				echo "Checking out source code from GitHub..."
				git branch: 'main', url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git', credentialsId: 'github-creds'
			}
		}

		stage('Run Tests') {
			steps {
				echo "Preparing environment and running tests..."
				sh 'touch ~/.bash_profile; source ~/.bash_profile; mvn -version'
				script {
					def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"
					if (params.TEST_CLASS?.trim()) { mvnCmd += " -Dtest=${params.TEST_CLASS}" }
					if (params.TEST_METHOD?.trim()) { mvnCmd += " -Dtest=${params.TEST_METHOD}" }
					if (params.GROUPS?.trim()) { mvnCmd += " -Dgroups=${params.GROUPS}" }
					if (params.BROWSER?.trim()) { mvnCmd += " -Dbrowser=${params.BROWSER}" }
					if (params.THREAD_COUNT?.trim()) { mvnCmd += " -Dthread.count=${params.THREAD_COUNT}" }

					echo "================================="
					echo "Running command:"
					echo mvnCmd
					echo "================================="

					sh mvnCmd
				}
			}
		}
	}

	post {
		always {
			publishHTML(target: [
				reportName: 'Extent Report',
				reportDir: env.REPORT_DIR,
				reportFiles: 'index.html',
				keepAll: true,
				alwaysLinkToLastBuild: true,
				allowMissing: false
			])
			archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*.html", allowEmptyArchive: true
			echo "Extent Report URL: ${env.BUILD_URL}artifact/${env.REPORT_DIR}/index.html"
		}
	}
}
