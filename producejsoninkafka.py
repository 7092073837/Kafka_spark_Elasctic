# -*- coding: utf-8 -*-
"""
Created on Sat May 11 13:12:41 2019

@author: I2103
"""


import random 
import pandas as pd
import numpy as np
from kafka import KafkaProducer 
from kafka import KafkaConsumer
import json 
from bson import json_util
from time import time, sleep
from datetime import datetime,timedelta

producer = KafkaProducer(bootstrap_servers='localhost:9092')

shop_items=['Noodles','Flour','Rice','Pancake Mix','Toilet Soap','Ball Pen','Shampoo','Olive Oil','Banana','Pomegranate','Marshmallows','Tropicana','Hair Oil','Room Freshener','Deodorant','Incense Sticks','Coffee Beans','Chamonile Tea','Marker','Napkins']
payment = ['Cash','Debit Card','Credit Card','PayTm']


tr_data={}
df_final=pd.DataFrame()
count = 0
for j in range(1,20000):
    count = count +1
    #print(count)
    df=pd.DataFrame()
    for i in range(random.randint(500,500)):
        tr_data_i={}
        tr_data_i['cust_id']=j
        tr_data_i['item']=random.choice(shop_items)
        tr_data_i['units']=random.randint(1,20)
        tr_data_i['unit_price']=np.random.normal(75,30,1)
        tr_data_i['hour_of_day']=np.random.normal(15,2.5,1)
        tr_data_i['payment_method'] = random.choice(payment)
        tr_data_i['cost_price'] = np.random.normal(50,10,1)
        for k in  str(random.randint(1,30)):
            k = int(k)
            tr_data_i['Date'] = (datetime.today() - timedelta(days=k)).strftime('%Y-%m-%d')
            break
        
        data3 = tr_data_i
        df = pd.DataFrame(tr_data_i)
        df_final=df_final.append(df)

        #producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'));

        data3['unit_price'] = float(data3['unit_price'])
        data3['cost_price'] = float(data3['cost_price'])
        data3['hour_of_day'] = float(data3['hour_of_day'])
       # f= open('/home/indium/testing/1.json','a')
       # f.write(str(data3))
    
        #producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda x: json.dumps(x).encode('utf-8'));
        #producer.send('test2', value=data3);
    
        #sdf_final.to_csv('StaticData2.csv',mode='a', encoding='utf-8',header=True)
    
        #sleep(0.01);
	producer.send('test', json.dumps(data3 , indent=4, default=json_util.default).encode('utf-8'))
        print(data3)

