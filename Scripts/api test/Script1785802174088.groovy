import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

String baseUrl = 'https://jsonplaceholder.typicode.com'

/* ---------- 1) GET a single post ---------- */
RequestObject getReq = new RequestObject('getPost')
getReq.setRestUrl(baseUrl + '/posts/1')
getReq.setRestRequestMethod('GET')

ResponseObject getRes = WS.sendRequest(getReq)

WS.verifyResponseStatusCode(getRes, 200)
WS.verifyElementPropertyValue(getRes, 'id', 1)
KeywordUtil.logInfo('GET body: ' + getRes.getResponseText())

/* ---------- 2) POST a new post ---------- */
RequestObject postReq = new RequestObject('createPost')
postReq.setRestUrl(baseUrl + '/posts')
postReq.setRestRequestMethod('POST')

ArrayList<TestObjectProperty> headers = new ArrayList<>()
headers.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json; charset=UTF-8'))
postReq.setHttpHeaderProperties(headers)

postReq.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(
    '{ "title": "katalon", "body": "hello api", "userId": 1 }'))

ResponseObject postRes = WS.sendRequest(postReq)

WS.verifyResponseStatusCode(postRes, 201)
WS.verifyElementPropertyValue(postRes, 'title', 'katalon')

def newId = WS.getElementPropertyValue(postRes, 'id')
KeywordUtil.logInfo('Created post id: ' + newId)