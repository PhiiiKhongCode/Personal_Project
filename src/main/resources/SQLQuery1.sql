create
database ShoppingCart;
drop
database ShoppingCart;

use
ShoppingCart;


create table Categories
(
    Id     int auto_increment primary key,
    `Name` nvarchar(80)
);

create table Products
(
    Id         int auto_increment primary key,
    `Name`     nvarchar(50),
    `Image`    text,
    Price      decimal,
    CreateDate date,
    Available  int,
    CategoryId int
);

create table `Roles`
(
    Id     int auto_increment primary key,
    `Name` enum('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')
);

create table Accounts
(
    Username   varchar(80) unique primary key,
    `Password` varchar(100),
    Fullname   nvarchar(150),
    Email      varchar(200),
    Photo      text
);

create table `Orders`
(
    Id           int auto_increment primary key,
    CustomerName varchar(80),
    CreateDate   date,
    `Address`    nvarchar(300)
);

create table OrderDetails
(
    Id        int auto_increment primary key,
    OrderId   int,
    ProductId int,
    Price     decimal,
    Quantity  int
);

alter table Authorities
    add constraint RoleId foreign key (RoleId) references Roles (Id);

alter table Authorities
    add constraint Username foreign key (Username) references Accounts (Username);

alter table Products
    add constraint CategoryId foreign key (CategoryId) references Categories (Id);

alter table `Orders`
    add constraint CustomerName foreign key (CustomerName) references Accounts (Username) on delete cascade;

alter table OrderDetails
    add constraint OrderId foreign key (OrderId) references Orders (Id);

alter table OrderDetails
    add constraint ProductId foreign key (ProductId) references Products (Id);

insert into Roles(`Name`)
values ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN');

insert into Accounts(Username, `Password`, Fullname, Email, Photo)
values ('user1', 'user1', 'Luong Minh Cong', 'cong@gmail.com', null),
       ('user2', 'user2', 'Ha Minh Hang', 'hang@gmail.com', null),
       ('user3', 'user3', 'Nguyen Chi Dung', 'dung@gmail.com', null);


select *
from Roles;
insert into Authorities(Username, RoleId)
values ('user1', 1),
       ('user2', 2),
       ('user3', 3);

insert into Categories(`Name`)
values ('cate1'),
       ('cate2');

select *
from Categories;
insert into Products(`Name`, `Image`, Price, CreateDate, Available, CategoryId)
values ('product1', null, 12.78, curdate(), 20, 1),
       ('product2', null, 13.78, current_date(), 20, 2),
       ('product3', null, 14.78, curdate(), 20, 2);
select *
from Products