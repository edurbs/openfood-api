ALTER TABLE forma_pagamento ADD date_updated DATETIME NULL;
update forma_pagamento set date_updated = utc_timestamp;
alter table forma_pagamento modify date_updated DATETIME not null;