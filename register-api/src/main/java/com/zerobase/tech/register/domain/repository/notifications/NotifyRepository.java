package com.zerobase.tech.register.domain.repository.notifications;

import com.zerobase.tech.register.domain.notification.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify,Long> {
}
