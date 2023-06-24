package com.fribbleQ.evencity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.entity.AddressBook;
import com.fribbleQ.evencity.mapper.AddressBookMapper;
import com.fribbleQ.evencity.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
