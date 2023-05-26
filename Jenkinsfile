pipeline {
    agent {
        kubernetes {
        yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
              labels:
                jenkin-job: selenium-appium
            spec:
                containers:
                - name: appium
                  image: appium/appium
                  command:
                  - cat
                  tty: true
                  volumeMounts:
                  - name: shared-data
                    mountPath: /data
                - name: maven
                  image: maven:3.8.6-openjdk-11-slim
                  command:
                  - cat
                  tty: true
                  volumeMounts:
                  - name: shared-data
                    mountPath: /data
                - name: allure
                  image: frankescobar/allure-docker-service:2.19.0
                  command:
                  - cat
                  tty: true
                  volumeMounts:
                  - name: shared-data
                    mountPath: /data
                - name: jq
                  image: stedolan/jq:latest
                  command:
                  - cat
                  tty: true
                  volumeMounts:
                  - name: shared-data
                    mountPath: /data
                volumes:
                - name: shared-data
                  emptyDir: {}
            '''
        }
    }

    stages {
        stage('Run mobile tests'){
            // Run appium and test parallelly
            parallel {
                stage('Start Appium server'){
                    steps {
                        script {
                            container('appium') {
                                try {
                                    sh 'appium'
                                } catch (err) {
                                    echo "Appium server is not running"
                                }
                            }
                        }
                    }
                }

                stage('Run test'){
                    steps {
                        script {
                            // Define SauceLabs URL
                            def SAUCELABS_URL = 'https://ondemand.us-west-1.saucelabs.com:443/wd/hub'
                            // Install maven packages and run tests
                            withCredentials([usernamePassword(credentialsId: 'ngannguyen_saucelab', passwordVariable: 'SAUCELABS_ACCESSKEY', usernameVariable: 'SAUCELABS_USERNAME')]) {
                                container('maven') {
                                    try {
                                        sh '''
                                        mvn clean install
                                        mvn clean test -DsuiteFile=src/test/resources/Parallel.xml -Dsaucelab_username=${SAUCELABS_USERNAME} -Dsaucelab_accessKey=${SAUCELABS_ACCESSKEY} -Dsaucelab_URL=${SAUCELABS_URL}
                                        '''
                                    } catch (err) {
                                        echo "Test failed"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('publish report'){
            steps {
                script {
                    container('allure') {
                        try {
                            sh 'allure generate --clean'
                        }
                        catch (err) {
                            echo "Cannot generate allure report"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            // Archive test results
            archiveArtifacts artifacts: 'allure-results/**/*'
            // Publish test report for easy viewing
            publishHTML (target : [allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'allure-report',
            reportFiles: 'index.html',
            reportName: 'allure-report',
            reportTitles: '', 
            useWrapperFileDirectly: true])

            // Define Slack message blocks
            def blocks = [
                [
                    "type": "header",
                    "text": [
                        "type": "plain_text",
                        "text": "FINISHED TEST",
                    ]
                ],
                [
                    "type": "divider"
                ],
                [
                    "type": "section",
                    "text": [
                        "type": "mrkdwn",
                        "text": ":sunny: Job *${env.JOB_NAME}*'s result is ${currentBuild.currentResult}.\n*Summary:*"
                    ]
                ],
                [
                "type": "section",
                "text": [
                    "type": "mrkdwn",
                    "text": "```${result}```"
                    ]
                ],
                [
                    "type": "divider"
                ],
                [
                    "type": "section",
                    "text": [
                        "type": "mrkdwn",
                        "text": ":pushpin: More info at:\n• *Build URL:* ${env.BUILD_URL}console\n• *Allure Report:* ${env.BUILD_URL}allure-report"
                    ]
                ],
            ]

            // Send notification
            slackSend channel: 'automation-test-notifications', blocks: blocks, teamDomain: 'agileops', tokenCredentialId: 'jenkins-slack', botUser: true
        }
    }
}
