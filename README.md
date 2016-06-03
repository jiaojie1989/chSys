Price Compare System
===
# Info
## Version
> `0.1-alpha`

## Date
> `Fri Jun 03 16:04:03 CST 2016 `

## Author
> `jiaojie <thomasjiao@vip.qq.com>`

# Apis
## Base Url
> http://172.16.7.27:8080/test

## Projects
* cn
* hk
* us

## Update price
* url
> http://172.16.7.27:8080/test/{projectName}/price

* http method
> `POST`

* json data
```json
{
    "Symbol1": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    },
    "Symbol2": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    },
    "Symbol3": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    }
}
```
> If timestamp is not sended in http request, the default timestamp is system timestamp.

* return
> `ok` is ok.
>
> Other json string string is error.

## Add order
* url
> http://172.16.7.27:8080/test/{projectName}/order

* http method
> `POST`

* json data
```
{
    "orderId": {
        "orderId": "orderId",
        "symbol": "symbol",
        "wait": "0", // 0 for now price, 1 wait for next price
        "sid": "1", // user sid
        "type": "buy", // buy or sell
        "amount": "1", // order amount
        "price": "20.000", // order price
        "timestamp": 1464864251 // [opt]
    }
}
```
> If timestamp is not sended in http request, the default timestamp is system timestamp.
>
> This structure could contain more than one order detail.

* return
> `ok` is ok.
>
> Other json string string is error.

## Delete order
* url 
> http://172.16.7.27:8080/test/{projectName}/order

* http method
> `PUT`

* json data
```
{
        "orderId": "orderId",
        "symbol": "symbol",
        "type": "buy" // buy or sell
}
```

* return
> `true` on success
>
> `false` on failure

## Data receive
* url
> http://172.16.7.27:8080/test/{projectName}/result

* procotol
> websocket

* json data
```
{
    "canDeal":false,
    "deal":true,
    "detail":{
        "amount":879,
        "orderId":"0b420964ea7c7a5ed037744cd22a5bef",
        "sid":"9499786",
        "timestamp":1464863505
    },
    "orderPrice":{
        "price":26.82
    },
    "price":{
        "$ref":"$.orderPrice"
    },
    "project":{
        "name":"us"
    },
    "succSymbol":{
        "ask":{
            "price":14.82
        },
        "bid":{
            "price":14.8
        },
        "project":{
            "$ref":"$.project"
        },
        "symbol":{
            "name":"WB"
        },
        "symbolName":"WB",
        "timestamp":1464863506
    },
    "symbol":{
        "ask":{
            "price":13.15
        },
        "bid":{
            "price":13.13
        },
        "project":{
            "$ref":"$.project"
        },
        "symbol":{
            "name":"WB"
        },
        "symbolName":"WB",
        "timestamp":1464863504
    },
    "type":{
        "buyType":false,
        "sellType":true,
        "type":-1
    }
}
```
> One piece.

## Remaining orders
* url
> http://172.16.7.27:8080/test/{projectName}/<buy/sell>/{symbolName}

* method
> `GET`

* return
```
[{"canDeal":false,"deal":false,"detail":{"amount":598,"orderId":"1ab7f201624cffa5b792540f1adb5e70","sid":"501857","timestamp":1464939919},"orderPrice":{"price":11.46},"price":{"price":11.46},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":850,"orderId":"9b03f5792d8a8b4ae2972e449e3476f2","sid":"8634604","timestamp":1464939918},"orderPrice":{"price":22.82},"price":{"price":22.82},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939918},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":16,"orderId":"9ca6b7d6a35835512b5f8458c42c3997","sid":"480413","timestamp":1464939919},"orderPrice":{"price":42.7},"price":{"price":42.7},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":206,"orderId":"479c7ae50167f2b7981b345a25b6e171","sid":"9870986","timestamp":1464939919},"orderPrice":{"price":45.47},"price":{"price":45.47},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":696,"orderId":"ea00c819c03f7e24dbeced9c79aae326","sid":"3430173","timestamp":1464939919},"orderPrice":{"price":62.57},"price":{"price":62.57},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":620,"orderId":"e1366bb24f6ef3ef9526433c253d9c32","sid":"2816730","timestamp":1464939919},"orderPrice":{"price":66.12},"price":{"price":66.12},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":645,"orderId":"187ae25d152c90d9e92d9751327cb351","sid":"1355549","timestamp":1464939919},"orderPrice":{"price":73.76},"price":{"price":73.76},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}},{"canDeal":false,"deal":false,"detail":{"amount":484,"orderId":"463159477819a0280fdfde3f4c3ae06c","sid":"2954717","timestamp":1464939919},"orderPrice":{"price":92.87},"price":{"price":92.87},"project":{"name":"us"},"symbol":{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464939919},"type":{"buyType":false,"sellType":true,"type":-1}}]
```

## Current price in system
* url
> http://172.16.7.27:8080/test/{projectName}/price/{symbolName}

* method
> `GET`

* return
```
{"ask":{"price":-1.1},"bid":{"price":9.99999999999E10},"project":{"name":"us"},"symbol":{"name":"WB"},"symbolName":"WB","timestamp":1464940854}
```
