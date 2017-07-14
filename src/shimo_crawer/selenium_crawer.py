# !/usr/bin/env python3
# -*- coding: utf-8 -*-
import json
import time
import traceback
import sys

import requests
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys


name_passwd = {
    'name': '123456@163.com',
    'passwd': '123456'
}


def get_basic_info(driver, base_url, path):
    driver.get(base_url + path)
    print(driver.page_source)

    print(name_passwd['name'])
    print(name_passwd['passwd'])
    before = driver.current_url
    email = driver.find_element_by_name('email')
    email.send_keys(name_passwd['name'])
    time.sleep(2)
    password = driver.find_element_by_name('password')
    password.send_keys(name_passwd['passwd'])
    driver.find_element_by_id('bd-login-submit').click()
    time.sleep(15)
    after = driver.current_url
    if before == after:
        print('登录失败')


def get_desktop_info(driver, base_url, path):
    driver.get(base_url + path)
    print(driver.current_url)
    # print(driver.page_source)


def get_cookies(driver):
    cookie = [item["name"] + "=" + item["value"] for item in driver.get_cookies()]
    print(cookie)
    cookie_str = ';'.join(item for item in cookie)
    print(cookie_str)
    cookies = {}
    for line in cookie_str.split(';'):
        key, value = line.split('=', 1)  # 1代表只分一次，得到两个数据
        cookies[key] = value
    return cookies


def main():
    url = 'https://shimo.im'
    driver = webdriver.PhantomJS()
    shimo_cookies = get_cookies_from_file()

    r = requests.get("https://shimo.im/desktop", cookies=shimo_cookies)
    valid_cookie = True if r.text.find("数据组") >= 0 else False

    if shimo_cookies is None or not valid_cookie:
        get_basic_info(driver, url, '/login')
        shimo_cookies = get_cookies(driver)
        driver.quit()
        save_cookies_to_file(shimo_cookies)

    print(shimo_cookies)
    return shimo_cookies


def get_cookies_from_file():
    try:
        return json.load(open("shimo_cookie.dat"))
    except Exception as e:
        return None


def save_cookies_to_file(shimo_cookies):
    try:
        json.dump(shimo_cookies, open("shimo_cookie.dat", 'w'))
    except Exception as e:
        traceback.print_stack()



def test():
    url = 'https://shimo.im'
    # driver = webdriver.Chrome('/Users/one/Documents/chromedriver')
    driver = webdriver.PhantomJS('/Users/one/Documents/phantomjs/bin/phantomjs')
    driver.set_page_load_timeout(20)
    get_basic_info(driver, url, '/login')
    time.sleep(5)
    get_desktop_info(driver, url, '/desktop')
    shimo_cookies = get_cookies(driver)
    print(shimo_cookies)
    driver.quit()


if __name__ == '__main__':
    main()






