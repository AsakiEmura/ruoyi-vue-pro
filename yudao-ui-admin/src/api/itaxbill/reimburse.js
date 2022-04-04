import request from '@/utils/request'

// 创建发票报销信息
export function createReimburse(data) {
  return request({
    url: '/itaxbill/reimburse/create',
    method: 'post',
    data: data
  })
}

// 更新发票报销信息
export function updateReimburse(data) {
  return request({
    url: '/itaxbill/reimburse/update',
    method: 'put',
    data: data
  })
}

// 删除发票报销信息
export function deleteReimburse(id) {
  return request({
    url: '/itaxbill/reimburse/delete?id=' + id,
    method: 'delete'
  })
}

// 获得发票报销信息
export function getReimburse(id) {
  return request({
    url: '/itaxbill/reimburse/get?id=' + id,
    method: 'get'
  })
}

// 获得发票报销信息分页
export function getReimbursePage(query) {
  return request({
    url: '/itaxbill/reimburse/page',
    method: 'get',
    params: query
  })
}

// 导出发票报销信息 Excel
export function exportReimburseExcel(query) {
  return request({
    url: '/itaxbill/reimburse/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
